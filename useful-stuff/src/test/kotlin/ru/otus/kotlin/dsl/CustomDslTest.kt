package ru.otus.kotlin.dsl;

import ru.otus.kotlin.domain.Role
import org.junit.jupiter.api.Test

class CustomDslTest {

    @Test
    fun `test builder`() {
        val user = UserDsl.user {
            name {
                first = "Lolo"
                middle = "Ivanovich"
                last = "Ivanov"
            }
            contacts {
                email = "email@mail.ru"
                phone = "+7999112233"
            }
            roles {
                add(Role.USER)
                +Role.ADMIN
            }
            scopes {
                add("read")
                add("write")
                // not possible
//                roles {
//                    add(Role.ANONYMOUS)
//                }
                // not possible
//                scopes {
//                    add("hui")
//                }
            }
        }
        println(user)
    }
}
