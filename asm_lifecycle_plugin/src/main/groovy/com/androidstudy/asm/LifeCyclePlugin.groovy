package com.androidstudy.asm

import org.gradle.api.Plugin
import org.gradle.api.Project

public class LifeCyclePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        System.out.println("=============com.androidstudy.asm.LifeCyclePlugin=============")
    }
}