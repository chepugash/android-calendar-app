package com.practice.calendar.util

import com.practice.calendar.presentation.entity.EventPresentationEntity

typealias Click = () -> Unit

typealias EventsGroupedByTime = List<List<EventPresentationEntity>>?
