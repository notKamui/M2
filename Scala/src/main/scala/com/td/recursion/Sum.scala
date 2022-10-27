package com.td.recursion

object Sum {

  def closedForm(n: Long): Long = n * (n + 1) / 2

  def recursive(n: Long): Long =
    if (n == 0) 0
    else if (n > 0) n + recursive(n - 1)
    else throw new IllegalArgumentException("n must be positive")

  def tailrecursive(n: Long): Long = {
    @scala.annotation.tailrec
    def loop(n: Long, acc: Long): Long =
      if (n == 0) acc
      else if (n > 0) loop(n - 1, acc + n)
      else throw new IllegalArgumentException("n must be positive")

    loop(n, 0)
  }

}
