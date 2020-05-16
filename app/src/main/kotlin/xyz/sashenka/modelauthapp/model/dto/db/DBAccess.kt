package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import javax.persistence.*

@Entity
@Table(name = "accesses")
data class DBAccess @JvmOverloads constructor(
    @Expose @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    val id: Int = 0,

    @Expose @Column(name = "user_id")
    val userId: Int,

    @Expose @Column(name = "resource")
    val resource: String,

    @Expose @Column(name = "role")
    val role: String,

    @Version
    val version: Long = 1
)
