package br.com.rads.gistnator.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProviderImpl : ScheduleProvider {
    override fun io() = Schedulers.io()
    override fun ui() = AndroidSchedulers.mainThread()
}