package com.ironsource.aura.kronos.config

import com.ironsource.aura.dslint.annotations.DSLMandatory
import com.ironsource.aura.dslint.annotations.DSLint
import com.ironsource.aura.kronos.config.constraint.Constraint
import com.ironsource.aura.kronos.source.SourceDefinition
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

typealias SimpleConfig<T> = Config<T, T>

@DSLint
interface Defaulted<T> {

    @set:DSLMandatory(group = "default")
    var default: T

    @DSLMandatory(group = "default")
    fun default(cache: Boolean = true,
                provider: () -> T)

    @set:DSLMandatory(group = "default")
    var defaultRes: Int
}

interface Constrained<Test, Fallback> {

    fun constraint(name: String? = null,
                   block: Constraint<Test, Fallback?>.() -> Unit)
}

interface Processable<T> {

    fun process(processor: (T) -> T)
}

@DSLint
interface Adaptable<Raw, Actual> {

    @DSLMandatory
    fun adapt(block: Adapter<Raw, Actual>.() -> Unit)
}

@DSLint
interface Adapter<Raw, Actual> {

    @DSLMandatory
    fun get(block: (Raw) -> Actual?)

    fun set(block: (Actual) -> Raw?)
}

@DSLint
interface Config<Raw, Actual> :
        Defaulted<Actual>,
        Constrained<Raw, Actual>,
        Processable<Actual> {

    var key: String
    var sourceDefinition: SourceDefinition<out Any>
    var cached: Boolean
}

@DSLint
interface AdaptedConfig<Raw, Actual> :
        Config<Raw, Actual>,
        Adaptable<Raw, Actual>

interface ConfigProperty<T> : ReadWriteProperty<FeatureRemoteConfig, T>
interface ReadOnlyConfigProperty<T> : ReadOnlyProperty<FeatureRemoteConfig, T>