package com.programmer74.sdl1

import mu.KLogging

object DataMergerExtensions : KLogging()

fun <A, B, C> mergeCollections(
  collectionA: List<A>,
  collectionB: List<B>,
  comparatorAB: (A, B) -> Boolean,
  constructorA: (A) -> C,
  constructorAB: (A, B) -> C,
  constructorB: (B) -> C,
  uniquenessField: (C) -> Any
): List<C> {
  val mutA = collectionA.toMutableList()
  val mutB = collectionB.toMutableList()

  val result = ArrayList<C>()
  val uniqueResult = ArrayList<C>()

  val iterA = mutA.iterator()
  while (iterA.hasNext()) {
    val entityA = iterA.next()
    val entityB = mutB.firstOrNull { comparatorAB.invoke(entityA, it) }
    if (entityB == null) {
      result.add(constructorA.invoke(entityA))
      iterA.remove()
    } else {
      result.add(constructorAB.invoke(entityA, entityB))
      iterA.remove()
      mutB.remove(entityB)
    }
  }

  mutB.forEach {
    result.add(constructorB(it))
  }

  result.forEach { res ->
    if (uniqueResult.firstOrNull {
          uniquenessField.invoke(it) == uniquenessField.invoke(res)
        } == null) {
      uniqueResult.add(res)
    } else {
      DataMergerExtensions.logger.warn {
        "Found duplicate entity for key ${uniquenessField.invoke(res)} : $res"
      }
    }
  }

  return uniqueResult
}
