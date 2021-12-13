package com.gradlepluginstudy.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println '创建自己的gradle插件'
        // 创建一个扩展
        def extension = project.extensions.create("MyExtend", MyExtension)
        project.afterEvaluate {
            println "读取扩展配置的数据然后打印出来 ${extension.name}"
        }
    }
}