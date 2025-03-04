package nerd.tuxmobil.fahrplan.congress.dataconverters

import com.google.common.truth.Truth.assertThat
import info.metadude.android.eventfahrplan.commons.temporal.Moment
import info.metadude.android.eventfahrplan.database.models.Highlight
import info.metadude.android.eventfahrplan.database.models.Session.Companion.RECORDING_OPT_OUT_ON
import nerd.tuxmobil.fahrplan.congress.models.DateInfo
import nerd.tuxmobil.fahrplan.congress.models.Session
import org.junit.jupiter.api.Test
import org.threeten.bp.ZoneOffset
import info.metadude.android.eventfahrplan.database.models.Session as SessionDatabaseModel
import info.metadude.android.eventfahrplan.network.models.Session as SessionNetworkModel
import nerd.tuxmobil.fahrplan.congress.models.Session as SessionAppModel

class SessionExtensionsTest {

    @Test
    fun `toSessionDatabaseModel returns a database session derived from an app session`() {
        val session = SessionDatabaseModel(
                sessionId = "7331",
                abstractt = "Lorem ipsum",
                dayIndex = 3,
                date = "2015-08-13",
                dateUTC = 1439478900000L,
                description = "Lorem ipsum dolor sit amet",
                duration = 45,
                feedbackUrl = "https://talks.mrmcd.net/2018/talk/V3FUNG/feedback",
                hasAlarm = true,
                isHighlight = true,
                language = "en",
                links = "[Website](https://www.example.com/path)",
                relativeStartTime = 1035,
                recordingLicense = "CC 0",
                recordingOptOut = RECORDING_OPT_OUT_ON,
                roomName = "Simulacron-3",
                roomIdentifier = "88888888-4444-4444-4444-121212121212",
                roomIndex = 17,
                speakers = "John Doe; Noah Doe",
                startTime = 1036,
                slug = "lorem",
                subtitle = "My subtitle",
                timeZoneOffset = 3600,
                title = "My title",
                track = "Security & Hacking",
                type = "tutorial",
                url = "https://talks.mrmcd.net/2018/talk/V3FUNG",

                changedDay = true,
                changedDuration = true,
                changedIsCanceled = true,
                changedIsNew = true,
                changedLanguage = true,
                changedRecordingOptOut = true,
                changedRoomName = true,
                changedSpeakers = true,
                changedSubtitle = true,
                changedTime = true,
                changedTitle = true,
                changedTrack = true
        )
        assertThat(session.toSessionAppModel().toSessionDatabaseModel()).isEqualTo(session)
    }

    @Test
    fun `toSessionAppModel returns an app session derived from a network session`() {
        val sessionNetworkModel = SessionNetworkModel(
                sessionId = "7331",
                abstractt = "Lorem ipsum",
                dayIndex = 3,
                date = "2015-08-13",
                dateUTC = 1439478900000L,
                description = "Lorem ipsum dolor sit amet",
                duration = 45,
                feedbackUrl = "https://talks.mrmcd.net/2018/talk/V3FUNG/feedback",
                hasAlarm = true,
                isHighlight = true,
                language = "en",
                links = "[Website](https://www.example.com/path)",
                relativeStartTime = 1035,
                recordingLicense = "CC 0",
                recordingOptOut = RECORDING_OPT_OUT_ON,
                roomName = "Simulacron-3",
                roomGuid = "88888888-4444-4444-4444-121212121212",
                roomIndex = 17,
                speakers = "John Doe;Noah Doe",
                startTime = 1036,
                slug = "lorem",
                subtitle = "My subtitle",
                timeZoneOffset = 3600,
                title = "My title",
                track = "Security & Hacking",
                type = "tutorial",
                url = "https://talks.mrmcd.net/2018/talk/V3FUNG",

                changedDayIndex = true,
                changedDuration = true,
                changedIsCanceled = true,
                changedIsNew = true,
                changedLanguage = true,
                changedRecordingOptOut = true,
                changedRoomName = true,
                changedSpeakers = true,
                changedSubtitle = true,
                changedStartTime = true,
                changedTitle = true,
                changedTrack = true
        )
        val sessionAppModel = SessionAppModel("7331").apply {
            abstractt = "Lorem ipsum"
            day = 3
            date = "2015-08-13"
            dateUTC = 1439478900000L
            description = "Lorem ipsum dolor sit amet"
            duration = 45
            feedbackUrl = "https://talks.mrmcd.net/2018/talk/V3FUNG/feedback"
            hasAlarm = true
            highlight = true
            lang = "en"
            links = "[Website](https://www.example.com/path)"
            relStartTime = 1035
            recordingLicense = "CC 0"
            recordingOptOut = RECORDING_OPT_OUT_ON
            roomName = "Simulacron-3"
            roomIdentifier = "88888888-4444-4444-4444-121212121212"
            roomIndex = 17
            speakers = listOf("John Doe", "Noah Doe")
            startTime = 1036
            slug = "lorem"
            subtitle = "My subtitle"
            timeZoneOffset = ZoneOffset.ofTotalSeconds(3600)
            title = "My title"
            track = "Security & Hacking"
            type = "tutorial"
            url = "https://talks.mrmcd.net/2018/talk/V3FUNG"

            changedDay = true
            changedDuration = true
            changedIsCanceled = true
            changedIsNew = true
            changedLanguage = true
            changedRecordingOptOut = true
            changedRoomName = true
            changedSpeakers = true
            changedSubtitle = true
            changedTime = true
            changedTitle = true
            changedTrack = true
        }
        assertThat(sessionNetworkModel.toSessionAppModel()).isEqualTo(sessionAppModel)
    }

    @Test
    fun `toDateInfo returns a DateInfo object derived from a session`() {
        val session = Session("")
        session.date = "2015-08-13"
        session.day = 3
        val dateInfo = DateInfo(3, Moment.parseDate("2015-08-13"))
        assertThat(session.toDateInfo()).isEqualTo(dateInfo)
    }

    @Test
    fun `toDayRanges returns a list of day ranges derived from a list of sessions`() {
        val session0 = Session("")
        session0.date = "2019-08-02"
        session0.day = 2

        val session1 = Session("")
        session1.date = "2019-08-01"
        session1.day = 1

        val session1Copy = Session("")
        session1Copy.date = "2019-08-01"
        session1Copy.day = 1

        val sessions = listOf(session0, session1, session1Copy)
        val dayRanges = sessions.toDayRanges()

        assertThat(dayRanges.size).isEqualTo(2)
        assertThat(dayRanges[0].startsAt.dayOfMonth).isEqualTo(1)
        assertThat(dayRanges[0].startsAt.hour).isEqualTo(0)
        assertThat(dayRanges[1].startsAt.dayOfMonth).isEqualTo(2)
        assertThat(dayRanges[1].startsAt.hour).isEqualTo(0)

        assertThat(dayRanges[0].endsAt.dayOfMonth).isEqualTo(1)
        assertThat(dayRanges[0].endsAt.hour).isEqualTo(23)
        assertThat(dayRanges[1].endsAt.dayOfMonth).isEqualTo(2)
        assertThat(dayRanges[1].endsAt.hour).isEqualTo(23)
    }

    @Test
    fun `toHighlightDatabaseModel returns a Highlight object derived from a session`() {
        val session = Session("4723").apply {
            highlight = true
        }
        val highlight = Highlight(sessionId = 4723, isHighlight = true)
        assertThat(session.toHighlightDatabaseModel()).isEqualTo(highlight)
    }

    @Test
    fun `sanitize moves the subtitle property value to the title property value if empty`() {
        val session = Session("").apply {
            subtitle = "Lorem ipsum"
            title = ""
        }.sanitize()
        val expected = Session("").apply {
            subtitle = ""
            title = "Lorem ipsum"
        }
        assertThat(session).isEqualTo(expected)
    }

    @Test
    fun `sanitize clears the subtitle property value if the title property matches`() {
        val session = Session("").apply {
            subtitle = "Lorem ipsum"
            title = "Lorem ipsum"
        }.sanitize()
        val expected = Session("").apply {
            subtitle = ""
            title = "Lorem ipsum"
        }
        assertThat(session).isEqualTo(expected)
    }

    @Test
    fun `sanitize keeps the subtitle property value if the title property value differs`() {
        val session = Session("").apply {
            subtitle = "Dolor sit amet"
            title = "Lorem ipsum"
        }.sanitize()
        val expected = Session("").apply {
            subtitle = "Dolor sit amet"
            title = "Lorem ipsum"
        }
        assertThat(session).isEqualTo(expected)
    }

    @Test
    fun `sanitize clears the abstractt property value if the description property matches`() {
        val session = Session("").apply {
            abstractt = "Lorem ipsum"
            description = "Lorem ipsum"
        }.sanitize()
        val expected = Session("").apply {
            abstractt = ""
            description = "Lorem ipsum"
        }
        // The "abstractt" and "description" fields are not part of Session#equals for some reason.
        assertThat(session.abstractt).isEqualTo(expected.abstractt)
        assertThat(session.description).isEqualTo(expected.description)
    }

    @Test
    fun `sanitize keeps the abstractt property value if the description property value differs`() {
        val session = Session("").apply {
            abstractt = "Lorem ipsum"
            description = "Dolor sit amet"
        }.sanitize()
        val expected = Session("").apply {
            abstractt = "Lorem ipsum"
            description = "Dolor sit amet"
        }
        // The "abstractt" and "description" fields are not part of Session#equals for some reason.
        assertThat(session.abstractt).isEqualTo(expected.abstractt)
        assertThat(session.description).isEqualTo(expected.description)
    }

    @Test
    fun `sanitize moves the abstractt property value to the description property value if empty`() {
        val session = Session("").apply {
            abstractt = "Lorem ipsum"
            description = ""
        }.sanitize()
        val expected = Session("").apply {
            abstractt = ""
            description = "Lorem ipsum"
        }
        // The "abstractt" and "description" fields are not part of Session#equals for some reason.
        assertThat(session.abstractt).isEqualTo(expected.abstractt)
        assertThat(session.description).isEqualTo(expected.description)
    }

    @Test
    fun `sanitize clears the subtitle property value if the speakers property value matches`() {
        val session = Session("").apply {
            speakers = listOf("Luke Skywalker")
            subtitle = "Luke Skywalker"
            title = "Some title"
        }.sanitize()
        val expected = Session("").apply {
            speakers = listOf("Luke Skywalker")
            subtitle = ""
            title = "Some title"
        }
        assertThat(session).isEqualTo(expected)
    }

    @Test
    fun `sanitize keeps the subtitle property value if the speakers property value differs`() {
        val session = Session("").apply {
            speakers = listOf("Darth Vader")
            subtitle = "Lorem ipsum"
            title = "Some title"
        }.sanitize()
        val expected = Session("").apply {
            speakers = listOf("Darth Vader")
            subtitle = "Lorem ipsum"
            title = "Some title"
        }
        assertThat(session).isEqualTo(expected)
    }

    @Test
    fun `sanitize copies the non-empty type property value to the track property if empty`() {
        val session = Session("").apply {
            track = ""
            type = "Workshop"
        }.sanitize()
        val expected = Session("").apply {
            track = "Workshop"
            type = "Workshop"
        }
        assertThat(session).isEqualTo(expected)
    }

}
