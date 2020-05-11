package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GeneratedValue

@Entity
@Table(name = "USER_SESSION")
data class DBUserSession @JvmOverloads constructor(
    @Expose @Id @GeneratedValue @Column(name = "ID")
    val id: Int = 0,

    @Expose @Column(name = "ACCESS_ID")
    val accessId: Int,

    @Expose @Column(name = "START_DATE")
    val dateStart: Date,

    @Expose @Column(name = "END_DATE")
    val dateEnd: Date,

    @Expose @Column(name = "VOLUME")
    val volume: Int
)
