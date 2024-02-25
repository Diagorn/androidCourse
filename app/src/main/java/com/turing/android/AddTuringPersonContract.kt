package com.turing.android

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.turing.android.AddTuringPersonActivity.Companion.TURING_PERSON_RESULT
import com.turing.android.dto.TuringPerson
import com.turing.android.utils.getParcelableObj

class AddTuringPersonContract: ActivityResultContract<Long, TuringPerson?>() {
    override fun createIntent(context: Context, input: Long): Intent {
        return Intent(context, AddTuringPersonActivity::class.java)
            .putExtra(NEXT_PERSON_ID, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): TuringPerson? {
        if (resultCode != RESULT_OK) {
            Log.e(TURING_PERSON_RESULT, "Не удалось создать нового активиста")
            return null
        }

        return intent?.extras.getParcelableObj(TURING_PERSON_RESULT)
    }
}
