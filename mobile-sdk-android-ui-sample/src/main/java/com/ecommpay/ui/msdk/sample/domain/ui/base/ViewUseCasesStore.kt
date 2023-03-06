package com.ecommpay.ui.msdk.sample.domain.ui.base

object ViewUseCasesStore {
    private val mMap = HashMap<String, ViewUseCaseContract<*, *>>()

    private fun <VUC : ViewUseCaseContract<*, *>> put(key: String, viewUseCase: VUC): VUC {
        val oldViewUC = mMap.put(key, viewUseCase)
        oldViewUC?.clear()
        return viewUseCase
    }

    fun <VUC : ViewUseCaseContract<*, *>> get(key: String, factory: () -> VUC): VUC =
        (mMap.get(key) as VUC?) ?: put(key, factory())

    fun <VUC : ViewUseCaseContract<*, *>> getOrNull(key: String): VUC? = (mMap.get(key) as VUC?)

    fun deleteExcept(noDeletekeys: List<String>) {
        mMap.filter { map -> noDeletekeys.all { it != map.key } }.forEach { map ->
            map.value.clear()
            mMap.remove(map.key)
        }
    }
}

inline fun <reified VUC : ViewUseCaseContract<*, *>> viewUseCase(
    key: String,
    noinline factory: () -> VUC,
): VUC = ViewUseCasesStore.get(key, factory)

