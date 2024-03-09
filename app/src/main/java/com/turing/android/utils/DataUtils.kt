package com.turing.android.utils

import com.turing.android.dto.TuringPerson

fun getTestTuringPersons(): List<TuringPerson> {
    return listOf(
        TuringPerson(
            identifyer = 1L,
            name = "Миша",
            surname = "Гасин",
            avatarUrl = "https://sun9-36.userapi.com/impg/qD9PX4NgiXuG4FUszmcfE3mZJZ6ezpbQElRKMA/_MYO8CsGus0.jpg?size=577x1280&quality=95&sign=f390e7a67e9aaaa4e270b8218d4a4608&type=album",
            age = 24,
            isStudent = false
        ),
        TuringPerson(
            identifyer = 2L,
            name = "Соня",
            surname = "Марчинская",
            avatarUrl = "https://sun9-55.userapi.com/impf/mhMnwAXgGodC2itZ6TObehNcL0TlXGXDOL4hAw/0_NeGUt8koc.jpg?size=1440x2160&quality=95&sign=1ab3d6e06f1f648009cb8dee09513d16&type=album",
            age = 21,
            isStudent = false
        ),
        TuringPerson(
            identifyer = 3L,
            name = "Паша",
            surname = "Жуков",
            avatarUrl = "https://sun9-75.userapi.com/impg/wo_Iuxv4HqlHtHt_EgoFEKUMEVI9x4dSpVbg9g/rYZgUwIRBvM.jpg?size=802x492&quality=95&sign=72d99932f31961ede28130b9a73b2741&type=album",
            age = 24,
            isStudent = false
        ),
        TuringPerson(
            identifyer = 4L,
            name = "Саша",
            surname = "Фофанов",
            avatarUrl = "https://sun9-33.userapi.com/impg/hToLIsNdNHLv887iy_MYd--5BH70hKLrdXywWg/JTRoaV28q2M.jpg?size=1440x2160&quality=95&sign=f6904440f07d7caf094068dd0005807a&type=album",
            age = 21,
            isStudent = false
        ),
    )
}