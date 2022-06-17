package io.github.donghune.api.serialization.serializers.exceptions

class MaterialNotFoundException(val material: String) :
    Exception("Could not deserialize the Material=$material because it do not exist.")