package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import javax.persistence.*

@Entity
@Table(name = "ACCESS")
data class DBAccess(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Expose
    val id: Int,

    @Column(name = "USER_ID")
    @Expose
    val userId: Int,

    @Column(name = "RESOURCE")
    @Expose
    val resource: String,

    @Column(name = "ROLE")
    @Expose
    val role: String
)
