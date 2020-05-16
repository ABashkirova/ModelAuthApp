package xyz.sashenka.modelauthapp.model.dto.db

import com.google.gson.annotations.Expose
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user_session")
data class DBUserSession @JvmOverloads constructor(
    @Expose @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    val id: Int = 0,

    @Expose @Column(name = "access_id")
    val accessId: Int,

    @Expose @Column(name = "start_date")
    val dateStart: Date,

    @Expose @Column(name = "end_date")
    val dateEnd: Date,

    @Expose @Column(name = "volume")
    val volume: Int,

    @Version
    val version: Long = 1
)
