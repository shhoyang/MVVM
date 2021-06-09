package com.mvvm.compiler

import com.mvvm.annotation.*
import com.mvvm.compiler.Utils.isNotEmpty
import java.util.*
import java.util.regex.Pattern
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/**
 * @author Yang Shihao
 */
class AnnotationMeta(
    elementUtils: Elements,
    private val typeUtils: Types,
    private val fieldFeatureSet: AnnotationFieldFeatureSet,
    private val typeElement: TypeElement
) {

    /**
     * 包名
     */
    val packageName: String

    /**
     * 注解类的类名
     */
    val annotatedClassName: String

    /**
     * 生成类的类名
     */
    val generateClassName: String

    /**
     * 支持的字段注解
     */
    private val supportFiledAnnotation: MutableSet<String>

    /**
     * 扫描到的字段
     */
    private val fieldList: MutableList<Field>

    init {
        packageName = elementUtils.getPackageOf(typeElement).qualifiedName.toString()
        annotatedClassName = typeElement.simpleName.toString()
        generateClassName = annotatedClassName + DaggerConstant.SUFFIX
        supportFiledAnnotation = HashSet(4)
        fieldList = ArrayList()
        scanField()
    }

    private fun scanField() {
        supportFiledAnnotation.add(InjectViewBinding::class.java.canonicalName)
        supportFiledAnnotation.add(Inject::class.java.canonicalName)
        if (typeElement.getAnnotation(AndroidEntryPoint::class.java).injectViewModel) {
            supportFiledAnnotation.add(InjectViewModel::class.java.canonicalName)
        }
        val superclassAnnotatedFieldMap: MutableMap<String, Field> = HashMap(8)
        scanSuperclassAnnotatedField(
            typeUtils.asElement(typeElement.superclass),
            superclassAnnotatedFieldMap
        )
        getSuperclassAnnotatedFieldType(superclassAnnotatedFieldMap)
        scanCurrentClassAnnotatedField()
    }

    /**
     * 获取父类中被注解的成员变量
     */
    private fun scanSuperclassAnnotatedField(element: Element, map: MutableMap<String, Field>) {
        val elements = element.enclosedElements
        for (e in elements) {
            if (e.kind == ElementKind.FIELD) {
                for (a in e.annotationMirrors) {
                    if (supportFiledAnnotation.contains(a.annotationType.toString())) {
                        map[a.annotationType.toString()] =
                            Field(a.annotationType.toString(), "", e.simpleName.toString())
                        break
                    }
                }
            }
        }
        if (element is TypeElement && element.getAnnotation(Base::class.java) != null) {
            scanSuperclassAnnotatedField(typeUtils.asElement(element.superclass), map)
        }
    }

    /**
     * 获取父类中被注解的成员变量的类型
     */
    private fun getSuperclassAnnotatedFieldType(map: Map<String, Field>) {
        val matcher = Pattern.compile(PARAMETER_REGEX).matcher(
            typeElement.superclass.toString()
        )
        if (!matcher.find()) {
            return
        }

        val types = matcher.group(1).split(",")
        for (type in types) {
            var key: String? = when {
                fieldFeatureSet.isViewBinding(type) -> InjectViewBinding::class.java.canonicalName
                fieldFeatureSet.isViewModel(type) -> InjectViewModel::class.java.canonicalName
                fieldFeatureSet.isAdapter(type) -> Inject::class.java.canonicalName
                else -> null
            } ?: continue
            map[key]?.filedType = type
        }
        map.forEach { (key: String?, field: Field) ->
            isNotEmpty(field.filedType) {
                fieldList.add(field)
            }
        }
    }

    /**
     * 获取当前类中被注解的成员变量
     */
    private fun scanCurrentClassAnnotatedField() {
        val elements = typeElement.enclosedElements
        for (e in elements) {
            if (e.kind == ElementKind.FIELD) {
                for (a in e.annotationMirrors) {
                    if (supportFiledAnnotation.contains(a.annotationType.toString())) {
                        fieldList.add(
                            Field(
                                a.annotationType.toString(),
                                e.asType().toString(),
                                e.simpleName.toString()
                            )
                        )
                        break
                    }
                }
            }
        }
    }

    fun getFieldList(): List<Field> {
        return fieldList
    }

    companion object {
        /**
         * 提取泛型
         */
        private const val PARAMETER_REGEX = "<(.+?)>"

        /**
         * 生成类的静态方法的参数名字
         */
        const val PARAMS_NAME_ACTIVITY = "activity"
    }
}