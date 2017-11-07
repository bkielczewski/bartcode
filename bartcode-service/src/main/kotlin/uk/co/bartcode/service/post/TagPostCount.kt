package uk.co.bartcode.service.post

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class TagPostCount(@Id val tag: String, val count: Long)