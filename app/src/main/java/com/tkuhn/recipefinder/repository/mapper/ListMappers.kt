package com.tkuhn.recipefinder.repository.mapper

class ListMapper<I, O>(
    private val mapper: ModelMapper<I, O>
) : ModelMapper<List<I>, List<O>> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}

class NullableInputListMapperImpl<I, O>(
    private val mapper: ModelMapper<I, O>
) : ModelMapper<List<I>?, List<O>> {
    override fun map(input: List<I>?): List<O> {
        return input?.map { mapper.map(it) }.orEmpty()
    }
}

class NullableOutputListMapperImpl<I, O>(
    private val mapper: ModelMapper<I, O>
) : ModelMapper<List<I>, List<O>?> {
    override fun map(input: List<I>): List<O>? {
        return if (input.isEmpty()) null else input.map { mapper.map(it) }
    }
}