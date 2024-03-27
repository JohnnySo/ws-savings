package org.soneira.savings.domain.model.vo.id

abstract class BaseId<T>(val value: T){
    override fun toString(): String {
        return "BaseId(value=$value)"
    }
}