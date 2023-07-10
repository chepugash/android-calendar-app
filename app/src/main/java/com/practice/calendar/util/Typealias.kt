package com.practice.calendar.util

import com.practice.calendar.domain.entity.EventInfo

typealias Click = () -> Unit

typealias EventsGroupedByTime = List<List<EventInfo>>?