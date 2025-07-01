#!/usr/bin/env python3
"""
Local gradle.properties generator for Android project with rename functionality

This script allows updating the gradle.properties file, replacing only 
the parameters specified in the JSON configuration.
Other parameters remain unchanged.

It also supports renaming files, folders, and content within files based on 
a rename configuration.

Usage:
    python gen_gradle_config.py --config config.json
    python gen_gradle_config.py --gradle-file app/gradle.properties --config config.json
    python gen_gradle_config.py --rename-config rename_config.json
"""

import argparse
import json
import sys
import re
import os
import shutil
from datetime import datetime, timezone
from pathlib import Path
from typing import Dict, Any, Optional, List, Tuple, Set
from dataclasses import dataclass, asdict
from enum import Enum


@dataclass
class GradleConfig:
    """
    Class for storing gradle.properties configuration
    
    Contains only the parameters that will be updated
    in the gradle.properties file
    """
    api_host: Optional[str] = None
    socket_host: Optional[str] = None
    
    def to_gradle_properties(self) -> Dict[str, str]:
        """
        Converts the configuration to gradle.properties format
        
        Returns only non-empty values as strings
        """
        properties = {}
        
        if self.api_host is not None:
            properties['API_HOST'] = self.api_host
            
        if self.socket_host is not None:
            properties['SOCKET_HOST'] = self.socket_host
            
        return properties


@dataclass
class RenameRule:
    """Single rename rule"""
    from_text: str
    to_text: str
    case_sensitive: bool = True
    
    def apply_to_text(self, text: str) -> str:
        """Apply rename rule to text content"""
        if self.case_sensitive:
            return text.replace(self.from_text, self.to_text)
        else:
            # Case-insensitive replacement while preserving original case pattern
            def replace_func(match):
                matched_text = match.group(0)
                # Preserve case pattern
                if matched_text.islower():
                    return self.to_text.lower()
                elif matched_text.isupper():
                    return self.to_text.upper()
                elif matched_text.istitle():
                    return self.to_text.capitalize()
                else:
                    return self.to_text
            
            pattern = re.escape(self.from_text)
            return re.sub(pattern, replace_func, text, flags=re.IGNORECASE)
    
    def apply_to_filename(self, filename: str) -> str:
        """Apply rename rule to filename"""
        return self.apply_to_text(filename)


@dataclass
class RenameConfig:
    """Configuration for rename operation"""
    rules: List[RenameRule]
    target_directories: List[str] = None
    file_extensions: List[str] = None
    exclude_directories: List[str] = None
    exclude_files: List[str] = None
    dry_run: bool = True
    backup: bool = True
    
    def __post_init__(self):
        if self.target_directories is None:
            self.target_directories = ["."]
        if self.file_extensions is None:
            self.file_extensions = [".java", ".kt", ".xml", ".json", ".gradle", ".properties"]
        if self.exclude_directories is None:
            self.exclude_directories = [".git", ".gradle", "build", ".idea"]
        if self.exclude_files is None:
            self.exclude_files = []


class ProjectRenamer:
    """
    Class for renaming files, folders, and content in a project
    """
    
    def __init__(self, config: RenameConfig, project_root: str = "."):
        self.config = config
        self.project_root = Path(project_root).resolve()
        self.backup_dir = None
        self.renamed_items = []
        self.modified_files = []
        
        print(f"🔄 Initializing project renamer")
        print(f"   Project root: {self.project_root}")
        print(f"   Rules: {len(self.config.rules)}")
        print(f"   Target directories: {self.config.target_directories}")
        print(f"   Dry run: {self.config.dry_run}")
        
        if self.config.backup and not self.config.dry_run:
            self.backup_dir = self.project_root / f"backup_{datetime.now().strftime('%Y%m%d_%H%M%S')}"
    
    def rename_project(self) -> None:
        """
        Main method to rename files, folders, and content
        """
        print(f"\n🚀 Starting project rename operation...")
        
        try:
            if self.config.backup and not self.config.dry_run:
                self._create_backup()
            
            # Step 1: Rename content in files
            self._rename_file_content()
            
            # Step 2: Rename files
            self._rename_files()
            
            # Step 3: Rename directories
            self._rename_directories()
            
            print(f"\n✅ Project rename operation completed!")
            self._print_summary()
            
        except Exception as e:
            print(f"\n❌ Error during rename operation: {e}")
            if self.backup_dir and self.backup_dir.exists():
                print(f"   Backup available at: {self.backup_dir}")
            sys.exit(1)
    
    def _create_backup(self) -> None:
        """Create backup of the project"""
        print(f"💾 Creating backup...")
        
        try:
            self.backup_dir.mkdir(exist_ok=True)
            
            for target_dir in self.config.target_directories:
                source_path = self.project_root / target_dir
                if source_path.exists():
                    if source_path.is_dir():
                        backup_path = self.backup_dir / target_dir
                        shutil.copytree(source_path, backup_path, ignore=shutil.ignore_patterns(*self.config.exclude_directories))
                    else:
                        shutil.copy2(source_path, self.backup_dir)
            
            print(f"   ✓ Backup created: {self.backup_dir}")
            
        except Exception as e:
            raise Exception(f"Failed to create backup: {e}")
    
    def _should_process_directory(self, dir_path: Path) -> bool:
        """Check if directory should be processed"""
        dir_name = dir_path.name
        return dir_name not in self.config.exclude_directories
    
    def _should_process_file(self, file_path: Path) -> bool:
        """Check if file should be processed"""
        file_name = file_path.name
        
        # Check exclude list
        if file_name in self.config.exclude_files:
            return False
        
        # Check file extension
        if file_path.suffix:
            return file_path.suffix.lower() in [ext.lower() for ext in self.config.file_extensions]
        else:
            # File without extension
            return True
    
    def _rename_file_content(self) -> None:
        """Rename content within files"""
        print(f"\n📝 Renaming content in files...")
        
        for target_dir in self.config.target_directories:
            target_path = self.project_root / target_dir
            
            if not target_path.exists():
                print(f"   ⚠️  Target directory not found: {target_path}")
                continue
            
            if target_path.is_file():
                # Single file
                if self._should_process_file(target_path):
                    self._process_file_content(target_path)
            else:
                # Directory - walk through all files
                for root, dirs, files in os.walk(target_path):
                    # Filter directories
                    dirs[:] = [d for d in dirs if d not in self.config.exclude_directories]
                    
                    root_path = Path(root)
                    
                    for file_name in files:
                        file_path = root_path / file_name
                        
                        if self._should_process_file(file_path):
                            self._process_file_content(file_path)
    
    def _process_file_content(self, file_path: Path) -> None:
        """Process content of a single file"""
        try:
            # Read file content
            with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
                original_content = f.read()
            
            # Apply rename rules
            modified_content = original_content
            content_changed = False
            
            for rule in self.config.rules:
                new_content = rule.apply_to_text(modified_content)
                if new_content != modified_content:
                    modified_content = new_content
                    content_changed = True
            
            # Save if changed
            if content_changed:
                if not self.config.dry_run:
                    with open(file_path, 'w', encoding='utf-8') as f:
                        f.write(modified_content)
                
                self.modified_files.append(str(file_path.relative_to(self.project_root)))
                print(f"   ✓ Modified: {file_path.relative_to(self.project_root)}")
            
        except Exception as e:
            print(f"   ❌ Error processing {file_path}: {e}")
    
    def _rename_files(self) -> None:
        """Rename files based on rules"""
        print(f"\n📁 Renaming files...")
        
        files_to_rename = []
        
        for target_dir in self.config.target_directories:
            target_path = self.project_root / target_dir
            
            if not target_path.exists():
                continue
            
            if target_path.is_file():
                # Single file
                new_name = self._apply_rules_to_filename(target_path.name)
                if new_name != target_path.name:
                    files_to_rename.append((target_path, target_path.parent / new_name))
            else:
                # Directory - collect all files to rename
                for root, dirs, files in os.walk(target_path):
                    dirs[:] = [d for d in dirs if d not in self.config.exclude_directories]
                    
                    root_path = Path(root)
                    
                    for file_name in files:
                        file_path = root_path / file_name
                        new_name = self._apply_rules_to_filename(file_name)
                        
                        if new_name != file_name:
                            new_path = root_path / new_name
                            files_to_rename.append((file_path, new_path))
        
        # Perform renaming
        for old_path, new_path in files_to_rename:
            self._rename_item(old_path, new_path, "file")
    
    def _rename_directories(self) -> None:
        """Rename directories based on rules"""
        print(f"\n📂 Renaming directories...")
        
        dirs_to_rename = []
        
        for target_dir in self.config.target_directories:
            target_path = self.project_root / target_dir
            
            if not target_path.exists() or not target_path.is_dir():
                continue
            
            # Collect directories to rename (bottom-up to avoid path conflicts)
            for root, dirs, files in os.walk(target_path, topdown=False):
                for dir_name in dirs:
                    if dir_name in self.config.exclude_directories:
                        continue
                    
                    dir_path = Path(root) / dir_name
                    new_name = self._apply_rules_to_filename(dir_name)
                    
                    if new_name != dir_name:
                        new_path = Path(root) / new_name
                        dirs_to_rename.append((dir_path, new_path))
        
        # Perform renaming
        for old_path, new_path in dirs_to_rename:
            self._rename_item(old_path, new_path, "directory")
    
    def _apply_rules_to_filename(self, filename: str) -> str:
        """Apply all rename rules to a filename"""
        result = filename
        
        for rule in self.config.rules:
            result = rule.apply_to_filename(result)
        
        return result
    
    def _rename_item(self, old_path: Path, new_path: Path, item_type: str) -> None:
        """Rename a file or directory"""
        try:
            if new_path.exists():
                print(f"   ⚠️  Target already exists: {new_path.relative_to(self.project_root)}")
                return
            
            if not self.config.dry_run:
                old_path.rename(new_path)
            
            self.renamed_items.append({
                'type': item_type,
                'old': str(old_path.relative_to(self.project_root)),
                'new': str(new_path.relative_to(self.project_root))
            })
            
            action = "Would rename" if self.config.dry_run else "Renamed"
            print(f"   ✓ {action} {item_type}: {old_path.name} → {new_path.name}")
            
        except Exception as e:
            print(f"   ❌ Error renaming {item_type} {old_path}: {e}")
    
    def _print_summary(self) -> None:
        """Print operation summary"""
        print(f"\n📊 Operation Summary:")
        print(f"   Files with modified content: {len(self.modified_files)}")
        print(f"   Renamed items: {len(self.renamed_items)}")
        
        if self.config.dry_run:
            print(f"   🔍 This was a dry run - no actual changes were made")
        
        if self.backup_dir and self.backup_dir.exists():
            print(f"   💾 Backup location: {self.backup_dir}")


class GradlePropertiesGenerator:
    """
    Main class for generating and updating the gradle.properties file
    
    Can read an existing file, update only the necessary parameters,
    and save while preserving formatting and comments
    """
    
    def __init__(self, config: GradleConfig, gradle_file: str = "gradle.properties"):
        self.config = config
        self.gradle_file = Path(gradle_file)
        self.build_time = datetime.now(timezone.utc)
        self.backup_created = False
        
        print(f"🔧 Initializing gradle.properties generator")
        print(f"   File: {self.gradle_file}")
        print(f"   Configuration: {len(self.config.to_gradle_properties())} parameters to update")
        
        # Create parent directory if it doesn't exist
        self.gradle_file.parent.mkdir(parents=True, exist_ok=True)
    
    def generate(self) -> None:
        """
        Generates or updates the gradle.properties file
        
        Preserves existing parameters and comments,
        updating only those specified in the configuration
        """
        print(f"\n📝 Updating gradle.properties...")
        
        try:
            # Create a backup of the existing file
            self._create_backup()
            
            # Read the existing file or create a new one
            existing_content = self._read_existing_file()
            
            # Update the content
            updated_content = self._update_properties(existing_content)
            
            # Write the updated file
            self._write_gradle_file(updated_content)
            
            print(f"\n✅ gradle.properties successfully updated!")
            self._validate_gradle_file()
            
        except Exception as e:
            print(f"\n❌ Error updating gradle.properties: {e}")
            self._restore_backup()
            sys.exit(1)
    
    def _create_backup(self) -> None:
        """Creates a backup of the existing file"""
        if self.gradle_file.exists():
            backup_path = self.gradle_file.with_suffix('.properties.backup')
            
            try:
                shutil.copy2(self.gradle_file, backup_path)
                self.backup_created = True
                print(f"   → Backup created: {backup_path}")
            except Exception as e:
                print(f"   ⚠️  Failed to create backup: {e}")
    
    def _read_existing_file(self) -> List[str]:
        """
        Reads the existing gradle.properties file
        
        Returns a list of strings preserving formatting
        """
        if not self.gradle_file.exists():
            print("   → Creating a new gradle.properties file")
            return [
                "# Gradle properties for Android project",
                "# Generated by gradle config generator",
                "",
            ]
        
        try:
            with open(self.gradle_file, 'r', encoding='utf-8') as f:
                content = f.readlines()
            
            print(f"   → Existing file read ({len(content)} lines)")
            return content
            
        except Exception as e:
            print(f"   → File read error: {e}")
            return []
    
    def _update_properties(self, existing_content: List[str]) -> List[str]:
        """
        Updates properties in the existing content
        
        Preserves comments and formatting, updating only
        the parameters specified in the configuration
        """
        properties_to_update = self.config.to_gradle_properties()
        updated_content = []
        updated_keys = set()
        
        print(f"   → Updating parameters: {list(properties_to_update.keys())}")
        
        # Iterate through existing lines
        for line in existing_content:
            line = line.rstrip('\n\r')
            
            # Check if the line is a property (key=value)
            property_match = re.match(r'^(\s*)([A-Za-z_][A-Za-z0-9_]*)\s*=\s*(.*)$', line)
            
            if property_match:
                indent, key, value = property_match.groups()
                
                if key in properties_to_update:
                    # Update the value, preserving indentation
                    new_value = properties_to_update[key]
                    updated_line = f"{indent}{key}={new_value}"
                    updated_content.append(updated_line)
                    updated_keys.add(key)
                    print(f"      ✓ {key}: {value} → {new_value}")
                else:
                    # Leave the line unchanged
                    updated_content.append(line)
            else:
                # Preserve comments and empty lines
                updated_content.append(line)
        
        # Add new properties that weren't in the file
        new_properties = set(properties_to_update.keys()) - updated_keys
        
        if new_properties:
            updated_content.append("")
            updated_content.append("# Auto-generated properties")
            
            for key in sorted(new_properties):
                value = properties_to_update[key]
                updated_content.append(f"{key}={value}")
                print(f"      + {key}={value}")
        
        return updated_content
    
    def _write_gradle_file(self, content: List[str]) -> None:
        """Writes the updated content to the file"""
        try:
            with open(self.gradle_file, 'w', encoding='utf-8') as f:
                for line in content:
                    f.write(line + '\n')
            
            print(f"   → gradle.properties written ({len(content)} lines)")
            
        except Exception as e:
            raise Exception(f"Failed to write gradle.properties: {e}")
    
    def _validate_gradle_file(self) -> None:
        """Validates the generated file"""
        print("\n🔍 Validating gradle.properties...")
        
        if not self.gradle_file.exists():
            raise Exception("gradle.properties file not found after generation")
        
        file_size = self.gradle_file.stat().st_size
        print(f"   ✓ File size: {file_size} bytes")
        
        # Check that all required parameters are present
        properties_to_check = self.config.to_gradle_properties()
        
        with open(self.gradle_file, 'r', encoding='utf-8') as f:
            content = f.read()
        
        missing_properties = []
        for key in properties_to_check:
            if f"{key}=" not in content:
                missing_properties.append(key)
        
        if missing_properties:
            print(f"   ⚠️  Missing parameters: {missing_properties}")
        else:
            print("   ✓ All parameters are present")
    
    def _restore_backup(self) -> None:
        """Restores the file from backup in case of error"""
        if self.backup_created:
            backup_path = self.gradle_file.with_suffix('.properties.backup')
            if backup_path.exists():
                try:
                    shutil.copy2(backup_path, self.gradle_file)
                    print(f"   → File restored from backup")
                except Exception as e:
                    print(f"   ❌ Failed to restore from backup: {e}")


def load_config_from_file(config_file: str) -> Dict[str, Any]:
    """
    Loads configuration from a JSON file
    
    Supports various configuration formats
    """
    try:
        with open(config_file, 'r', encoding='utf-8') as f:
            return json.load(f)
    except FileNotFoundError:
        print(f"❌ Configuration file {config_file} not found")
        sys.exit(1)
    except json.JSONDecodeError as e:
        print(f"❌ JSON parsing error in file {config_file}: {e}")
        sys.exit(1)


def create_sample_config_file(filename: str = "gradle_config.json") -> None:
    """Creates a sample configuration file"""
    sample_config = {
        "api_host": "https://api.example.com",
        "socket_host": "wss://socket.example.com",
    }
    
    with open(filename, 'w', encoding='utf-8', newline='') as f:
        json.dump(sample_config, f, indent=2)
    
    print(f"✅ Sample configuration file created: {filename}")
    print("   Edit the file and run the script with --config parameter")


def create_sample_rename_config_file(filename: str = "rename_config.json") -> None:
    """Creates a sample rename configuration file"""
    sample_config = {
        "rules": [
            {
                "from_text": "original_name",
                "to_text": "new_name",
                "case_sensitive": False
            },
            {
                "from_text": "OldCompany",
                "to_text": "NewCompany",
                "case_sensitive": True
            }
        ],
        "target_directories": ["."],
        "file_extensions": [".java", ".kt", ".xml", ".json", ".gradle", ".properties", ".md"],
        "exclude_directories": [".git", ".gradle", "build", ".idea", "node_modules"],
        "exclude_files": ["gen_config.py"],
        "dry_run": True,
        "backup": False
    }
    
    with open(filename, 'w', encoding='utf-8') as f:
        json.dump(sample_config, f, indent=2)
    
    print(f"✅ Sample rename configuration file created: {filename}")
    print("   Edit the file and run the script with --rename-config parameter")


def parse_gradle_config(config_data: Dict[str, Any]) -> GradleConfig:
    """
    Converts a configuration dictionary to a GradleConfig object
    
    Ignores unknown parameters and validates known ones
    """
    gradle_config = GradleConfig()
    
    # Mapping JSON keys to class fields
    field_mapping = {
        'api_host': 'api_host',
        'socket_host': 'socket_host',
    }
    
    for json_key, field_name in field_mapping.items():
        if json_key in config_data:
            value = config_data[json_key]
            
            setattr(gradle_config, field_name, value)
    
    return gradle_config


def parse_rename_config(config_data: Dict[str, Any]) -> RenameConfig:
    """
    Converts a configuration dictionary to a RenameConfig object
    """
    # Parse rules
    rules = []
    for rule_data in config_data.get('rules', []):
        rule = RenameRule(
            from_text=rule_data['from_text'],
            to_text=rule_data['to_text'],
            case_sensitive=rule_data.get('case_sensitive', True)
        )
        rules.append(rule)
    
    # Create RenameConfig
    rename_config = RenameConfig(
        rules=rules,
        target_directories=config_data.get('target_directories', ["."]),
        file_extensions=config_data.get('file_extensions', [".java", ".kt", ".xml", ".json", ".gradle", ".properties"]),
        exclude_directories=config_data.get('exclude_directories', [".git", ".gradle", "build", ".idea"]),
        exclude_files=config_data.get('exclude_files', []),
        dry_run=config_data.get('dry_run', True),
        backup=config_data.get('backup', True)
    )
    
    return rename_config


def main():
    """Main script function"""
    parser = argparse.ArgumentParser(
        description="gradle.properties generator and project renamer for Android project",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Usage examples:
  # Gradle config operations:
  %(prog)s --config gradle_config.json
  %(prog)s --gradle-file app/gradle.properties --config config.json
  %(prog)s --create-sample-config
  
  # Rename operations:
  %(prog)s --rename-config rename_config.json
  %(prog)s --create-sample-rename-config
        """
    )
    
    # Group for command line parameters
    cli_group = parser.add_argument_group('Command line parameters')
    cli_group.add_argument('--api-host', help='API server URL')
    cli_group.add_argument('--socket-host', help='Socket server URL')
    
    # Group for file operations
    file_group = parser.add_argument_group('File operations')
    file_group.add_argument('--config', help='Path to JSON configuration file')
    file_group.add_argument('--gradle-file', default='gradle.properties',
                           help='Path to gradle.properties file (default: gradle.properties)')
    file_group.add_argument('--create-sample-config', action='store_true',
                           help='Create a sample configuration file')
    
    # Group for rename operations
    rename_group = parser.add_argument_group('Rename operations')
    rename_group.add_argument('--rename-config', help='Path to JSON rename configuration file')
    rename_group.add_argument('--create-sample-rename-config', action='store_true',
                             help='Create a sample rename configuration file')
    
    args = parser.parse_args()
    
    # Handle special commands
    if args.create_sample_config:
        create_sample_config_file()
        return
    
    if args.create_sample_rename_config:
        create_sample_rename_config_file()
        return
    
    # Handle rename operation
    if args.rename_config:
        print(f"🔄 Loading rename configuration from file: {args.rename_config}")
        config_data = load_config_from_file(args.rename_config)
        rename_config = parse_rename_config(config_data)
        
        # Validate that we have rules to apply
        if not rename_config.rules:
            print("❌ No rename rules specified in configuration")
            sys.exit(1)
        
        # Execute rename operation
        renamer = ProjectRenamer(rename_config)
        renamer.rename_project()
        return
    
    # Determine gradle configuration source
    if args.config:
        # Load from file
        print(f"📄 Loading configuration from file: {args.config}")
        config_data = load_config_from_file(args.config)
        gradle_config = parse_gradle_config(config_data)
        
    else:
        print("❌ You must specify --config for configuration generation")
        print("   Or use rename operations with --rename-config")
        parser.print_help()
        sys.exit(1)
    
    # Check that there are parameters to update
    properties_to_update = gradle_config.to_gradle_properties()
    if not properties_to_update:
        print("❌ No parameters to update")
        sys.exit(1)
    
    # Generate gradle.properties
    generator = GradlePropertiesGenerator(gradle_config, args.gradle_file)
    generator.generate()


if __name__ == "__main__":
    main()
