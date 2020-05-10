package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "USER_SESSION")
data class DBUserSession(
    @Id
    @Column(name = "ID")
    @Expose
    val id: Int,

    @Column(name = "ACCESS_ID")
    @Expose
    val accessId: Int,

    @Column(name = "START_DATE")
    @Expose
    val dateStart: Date,

    @Column(name = "END_DATE")
    @Expose
    val dateEnd: Date,

    @Column(name = "VOLUME")
    @Expose
    val volume: Int
)
