package br.com.rads.gistnator.rx

import io.reactivex.Scheduler

interface ScheduleProvider {
    fun io() : Scheduler
    fun ui() : Scheduler
}