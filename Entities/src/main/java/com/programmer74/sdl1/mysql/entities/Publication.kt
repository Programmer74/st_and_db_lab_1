package com.programmer74.sdl1.mysql.entities

import javax.persistence.*

@Entity
@Table(name = "publication")
data class Publication(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Int? = null,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = false)
  val type: String,

  @Column(nullable = false)
  val language: String,

  @Column(nullable = false)
  val source: String,

  @Column(nullable = false)
  val pages: Int,

  @Column(nullable = false)
  val sourceType: String,

  @Column(nullable = false)
  val quoteIndex: Int,

  @Column(nullable = false)
  val date: Long,

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = [
        CascadeType.PERSIST,
        CascadeType.MERGE
      ])
  @JoinTable(
      name = "publication_author",
      joinColumns = [JoinColumn(name = "publication_id")],
      inverseJoinColumns = [JoinColumn(name = "person_id")])
  val authors: MutableList<Person> = ArrayList()
) {
  fun addAuthor(person: Person) {
    authors.add(person)
    person.publications.add(this)
  }

  override fun toString(): String {
    return "Publication(id=$id, name='$name', type='$type', language='$language', source='$source', " +
        "pages=$pages, sourceType='$sourceType', quoteIndex=$quoteIndex, date=$date, authors: ${authors.size})"
  }
}