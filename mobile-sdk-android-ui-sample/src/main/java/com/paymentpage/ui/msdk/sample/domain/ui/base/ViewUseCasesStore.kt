package com.paymentpage.ui.msdk.sample.domain.ui.base

//import timber.log.Timber
import kotlin.collections.HashMap

object ViewUseCasesStore {
    private val mMap = HashMap<String, ViewUseCaseContract<*, *>>()

    private fun <VUC : ViewUseCaseContract<*, *>> put(key: String, viewUseCase: VUC): VUC {
        val oldViewUC = mMap.put(key, viewUseCase)
        oldViewUC?.clear()
        return viewUseCase
    }

    fun <VUC : ViewUseCaseContract<*, *>> get(key: String, factory: () -> VUC): VUC =
        (mMap.get(key) as VUC?) ?: put(key, factory()).also { print() }

    fun <VUC : ViewUseCaseContract<*, *>> getOrNull(key: String): VUC? = (mMap.get(key) as VUC?)

    fun deleteExcept(noDeletekeys: List<String>) {
        mMap.filter { map -> noDeletekeys.all { it != map.key } }.forEach { map ->
            map.value.clear()
            mMap.remove(map.key)
        }
        print()
    }

    fun clear() {
        mMap.clear()
        print()
    }

    fun print() {
        //Timber.e("Usecases - [")
        val lastIndex = mMap.keys.size - 1
        mMap.keys.sortedBy { it.length }.forEachIndexed { index, key ->
            //if(index != lastIndex) Timber.e("$key,")
            //else Timber.e("$key]")
        }
    }
}

inline fun <reified VUC : ViewUseCaseContract<*, *>> viewUseCase(
    key: String,
    noinline factory: () -> VUC,
    viewUseCasesStore: ViewUseCasesStore = checkNotNull(ViewUseCasesStore) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

): VUC = ViewUseCasesStore.get(key, factory)

