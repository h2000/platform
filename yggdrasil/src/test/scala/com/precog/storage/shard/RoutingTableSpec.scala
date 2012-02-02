/*
 *  ____    ____    _____    ____    ___     ____ 
 * |  _ \  |  _ \  | ____|  / ___|  / _/    / ___|        Precog (R)
 * | |_) | | |_) | |  _|   | |     | |  /| | |  _         Advanced Analytics Engine for NoSQL Data
 * |  __/  |  _ <  | |___  | |___  |/ _| | | |_| |        Copyright (C) 2010 - 2013 SlamData, Inc.
 * |_|     |_| \_\ |_____|  \____|   /__/   \____|        All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU Affero General Public License as published by the Free Software Foundation, either version 
 * 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See 
 * the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this 
 * program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.precog.yggdrasil
package shard

import com.precog.common._

import com.precog.analytics.Path

import blueeyes.json.JsonAST._
import blueeyes.json.JPath

import org.specs2.mutable._
import org.specs2.matcher.{Matcher, MatchResult, Expectable}

import scala.collection.immutable.ListMap

class RoutingTableSpec extends Specification {
  
  "SingleColumnProjectionRoutingTable" should {

    "project an empty event to an empty set of projection actions" in {
      val rt = SingleColumnProjectionRoutingTable 

      val event: Set[(ColumnDescriptor, JValue)] = Set.empty

      val actions = rt.route(event)

      actions must_== Set.empty
    }

    "project an event with one property to a single projection action" in {
      val rt = SingleColumnProjectionRoutingTable 

      val event: List[(ColumnDescriptor, JValue)] = List(
        (ColumnDescriptor(Path("/a/b/"),JPath(".selector"), SLong, Ownership(Set())), JString("Test"))
      )

      val actions = rt.route(event.toSet)

      val expected : Set[ProjectionDescriptor] = sys.error("todo")//Set( (ProjectionDescriptor(event.map( _._1 ), Set()), event.map( _._2 )) )

      actions must_== expected 
    }.pendingUntilFixed

    "project an event with n properties to n projection actions" in {
      val rt = SingleColumnProjectionRoutingTable 

      val event: List[(ColumnDescriptor, JValue)] = List(
        (ColumnDescriptor(Path("/a/b/"),JPath(".selector"), SLong, Ownership(Set())), JString("Test")),
        (ColumnDescriptor(Path("/a/b/"),JPath(".selector.foo"), SLong, Ownership(Set())), JInt(1))
      )

      val actions = rt.route(event.toSet)

      val qss = event.map( _._1 )
      val vals = event.map( _._2 )

      val expected : Set[ProjectionDescriptor] = sys.error("todo")
        Set( (ProjectionDescriptor(ListMap() + (qss(0) -> 0), List() :+ (qss(0) -> ById) ), List(vals(0))),
             (ProjectionDescriptor(ListMap() + (qss(1) -> 0), List() :+ (qss(0) -> ById) ), List(vals(1))) )

      actions must_== expected 
    }.pendingUntilFixed
  }
}
