package uk.co.bartcode.service.post

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(DatePostCount.CompoundKey::class)
data class DatePostCount(@Id val year: Int, @Id val month: Int, val count: Long) {
    internal data class CompoundKey(val year: Int, val month: Int) : Serializable
}
