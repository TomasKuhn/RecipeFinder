package com.tkuhn.recipefinder.repository.mapper

class ListMapper<I, O>(
    private val mapper: ModelMapper<I, O>
) : ModelMapper<List<I>, List<O>> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}

class NullableInputListMapper<I, O>(
    private val mapper: ModelMapper<I, O>
) : ModelMapper<List<I>?, List<O>> {
    override fun map(input: List<I>?): List<O> {
        return input?.map { mapper.map(it) }.orEmpty()
    }
}

class NullableOutputListMapper<I, O>(
    private val mapper: ModelMapper<I, O>
) : ModelMapper<List<I>, List<O>?> {
    override fun map(input: List<I>): List<O>? {
        return if (input.isEmpty()) null else input.map { mapper.map(it) }
    }
}

fun <I, O> ModelMapper<I, O>.toListMapper(): ListMapper<I, O> {
    return ListMapper(this)
}

fun <I, O> ModelMapper<I, O>.toNullableInputListMapper(): NullableInputListMapper<I, O> {
    return NullableInputListMapper(this)
}

fun <I, O> ModelMapper<I, O>.toNullableOutputListMapper(): NullableOutputListMapper<I, O> {
    return NullableOutputListMapper(this)
}
