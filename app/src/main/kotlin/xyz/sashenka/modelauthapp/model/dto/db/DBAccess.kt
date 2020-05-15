package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import javax.persistence.*

@Entity
@Table(name = "ACCESS")
data class DBAccess @JvmOverloads constructor(
    @Expose @Id @GeneratedValue @Column(name = "ID")
    val id: Int = 0,

    @Expose @Column(name = "USER_ID")
    val userId: Int,

    @Expose @Column(name = "RESOURCE")
    val resource: String,

    @Expose @Column(name = "ROLE")
    val role: String,

    @Version
    val version: Long
)
