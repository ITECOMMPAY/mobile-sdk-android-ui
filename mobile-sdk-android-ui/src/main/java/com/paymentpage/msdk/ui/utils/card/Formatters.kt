package com.paymentpage.msdk.ui.utils.card

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

internal fun formatAmex(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 15) text.text.substring(0..14) else text.text
    var out = ""

    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i == 3 || i == 9 && i != 14) out += " "
    }
//    original - 345678901234564
//    transformed - 3456-7890123-4564
//    xxxx-xxxxxx-xxxxx
    /**
     * The offset translator should ignore the hyphen characters, so conversion from
     *  original offset to transformed text works like
     *  - The 4th char of the original text is 5th char in the transformed text. (i.e original[4th] == transformed[5th]])
     *  - The 11th char of the original text is 13th char in the transformed text. (i.e original[11th] == transformed[13th])
     *  Similarly, the reverse conversion works like
     *  - The 5th char of the transformed text is 4th char in the original text. (i.e  transformed[5th] == original[4th] )
     *  - The 13th char of the transformed text is 11th char in the original text. (i.e transformed[13th] == original[11th])
     */
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 9) return offset + 1
            if (offset <= 15) return offset + 2
            return 17
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 11) return offset - 1
            if (offset <= 17) return offset - 2
            return 15
        }
    }
    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

internal fun formatDinnersClub(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 14) text.text.substring(0..13) else text.text
    var out = ""

    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i == 3 || i == 9 && i != 13) out += " "
    }

//    xxxx-xxxxxx-xxxx
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 9) return offset + 1
            if (offset <= 14) return offset + 2
            return 16
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 11) return offset - 1
            if (offset <= 16) return offset - 2
            return 14
        }
    }
    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

internal fun formatOtherCardNumbers(text: AnnotatedString): TransformedText {

    val trimmed = if (text.text.length >= 19) text.text.substring(0..18) else text.text
    var out = ""

    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3) out += " "
    }
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 7) return offset + 1
            if (offset <= 11) return offset + 2
            if (offset <= 15) return offset + 3
            if (offset <= 19) return offset + 4
            return 23
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 8) return offset - 1
            if (offset <= 12) return offset - 2
            if (offset <= 16) return offset - 3
            if (offset <= 20) return offset - 4
            return 20
        }
    }

    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}
