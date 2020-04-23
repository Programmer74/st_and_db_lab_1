package com.programmer74.sdl1

import mu.KLogging
import org.springframework.data.jpa.repository.JpaRepository

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

fun <A, B> getOrLoadCollection(
  name: String,
  sourceCollection: List<A>,
  destinationRepository: JpaRepository<B, Int>,
  constructor: (A) -> B,
  dryRun: Boolean = false
): MutableList<B> {
  if (destinationRepository.findAll().isNotEmpty()) {
    DataMergerExtensions.logger.warn { "Repository $name is already dumped" }
  } else {
    DataMergerExtensions.logger.warn { "Dumping $name" }
    if (dryRun) DataMergerExtensions.logger.warn { "DRY RUN, NO CHANGES TO DB WILL BE APPLIED" }
    sourceCollection.map {
      constructor.invoke(it)
    }.forEach {
      if (!dryRun) destinationRepository.saveAndFlush(it)
    }
  }
  return destinationRepository.findAll().toMutableList()
}

fun <A, B> getOrLoadCollectionExcludingDuplicates(
  name: String,
  sourceCollection: List<A>,
  repository: JpaRepository<B, Int>,
  constructor: (A) -> B,
  comparator: (A, B) -> Boolean,
  skipStepsOnNonEmptyTable: Boolean
): MutableList<B> {
  if (skipStepsOnNonEmptyTable && repository.findAll().isNotEmpty()) {
    DataMergerExtensions.logger.warn { "Repository $name is already dumped" }
  } else {
    sourceCollection.map { sourceEntity ->
      val existing = repository.findAll().filter { comparator.invoke(sourceEntity, it) }
      if (existing.isNotEmpty()) {
        existing.single()
      } else {
        val new = constructor.invoke(sourceEntity)
        val saved = repository.saveAndFlush(new)
        saved
      }
    }
  }
  return repository.findAll().toMutableList()
}