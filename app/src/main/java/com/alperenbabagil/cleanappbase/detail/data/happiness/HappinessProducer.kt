package com.alperenbabagil.cleanappbase.detail.data.happiness

import kotlin.random.Random

class HappinessProducer {
    fun produce(userName : String) : Int{
        return 100 - Random.nextInt(10) -
           userName.length.times(2)
    }
}