import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.retrofit(){
    impl(Deps.Network.retrofit)
    impl(Deps.Network.logInterceptor)
}