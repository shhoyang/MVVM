package com.mvvm.compiler

import com.mvvm.annotation.DaggerAndroidApp
import com.mvvm.annotation.DaggerConstant
import javax.annotation.processing.RoundEnvironment

/**
 * @author Yang Shihao
 */
class AnnotationFieldFeatureSet(roundEnv: RoundEnvironment, options: Map<String, String>) {
    private val viewBindingFeatureSet: MutableSet<String>
    private val viewModelFeatureSet: MutableSet<String>
    private val adapterFeatureSet: MutableSet<String>

    init {
        viewBindingFeatureSet = HashSet()
        viewModelFeatureSet = HashSet()
        adapterFeatureSet = HashSet()
        getFeature(roundEnv, options)
    }

    private fun getFeature(roundEnv: RoundEnvironment, options: Map<String, String>) {
        roundEnv.getElementsAnnotatedWith(DaggerAndroidApp::class.java).forEach {
            it.getAnnotation(DaggerAndroidApp::class.java)?.apply {
                viewBindingFeatureSet.addFeatures(viewBindingFeatures)
                viewModelFeatureSet.addFeatures(viewModelFeatures)
                adapterFeatureSet.addFeatures(adapterFeatures)
            }
        }
        viewBindingFeatureSet.addFeature(options[DaggerConstant.KEY_VIEW_BINDING_FEATURE])
        viewModelFeatureSet.addFeature(options[DaggerConstant.KEY_VIEW_MODEL_FEATURE])
        adapterFeatureSet.addFeature(options[DaggerConstant.KEY_ADAPTER_FEATURE])
    }

    fun isViewBinding(qualifiedClassName: String) =
        viewBindingFeatureSet.hasFeature(qualifiedClassName)

    fun isViewModel(qualifiedClassName: String) = viewModelFeatureSet.hasFeature(qualifiedClassName)

    fun isAdapter(qualifiedClassName: String) = adapterFeatureSet.hasFeature(qualifiedClassName)
}