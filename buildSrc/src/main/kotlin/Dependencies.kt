import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    }

    object Material {
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object Navigation {
        const val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val featureModule =
            "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
        const val compose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"

        const val testing = "androidx.navigation:navigation-testing:${Versions.navigation}"
    }

    object Compose {
        const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val livedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val icons = "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val testing = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    }

    object Test {
        //test libs
        const val junit = "junit:junit:${Versions.junit}"
        const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    }

    val appLibraries = arrayListOf(
        Kotlin.stdlib,
        AndroidX.coreKtx,

        Material.material,

        Navigation.fragment,
        Navigation.ui,
        Navigation.featureModule,
        Navigation.compose,

        Compose.activity,
        Compose.ui,
        Compose.material,
        Compose.tooling,
        Compose.runtime,
        Compose.livedata,
        Compose.icons
    )

    val androidTestLibraries = arrayListOf(
        Test.extJUnit,
        Test.espressoCore,
        Navigation.testing,
        Compose.testing
    )

    val testLibraries = arrayListOf(
        Test.junit
    )
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}