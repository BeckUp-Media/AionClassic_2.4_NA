/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.commons.utils;

import java.util.List;

/**
 * @author Balancer
 */
public class Rnd
{
	private static final MTRandom rnd = new MTRandom();

	public static float get()
	{
		return rnd.nextFloat();
	}

	public static int get(int n)
	{
		return (int) Math.floor(rnd.nextDouble() * n);
	}

	public static int get(int min, int max)
	{
		return min + (int) Math.floor(rnd.nextDouble() * (max - min + 1));
	}

	public static boolean chance(int chance)
	{
		return (chance >= 1) && ((chance > 99) || (nextInt(99) + 1 <= chance));
	}

	public static boolean chance(double chance)
	{
		return nextDouble() <= chance / 100.0D;
	}

	public static <E> E get(E[] list)
	{
		return list[get(list.length)];
	}

	public static int get(int[] list)
	{
		return list[get(list.length)];
	}

	public static <E> E get(List<E> list)
	{
		return list.get(get(list.size()));
	}

	public static int nextInt(int n)
	{
		return (int) Math.floor(rnd.nextDouble() * n);
	}

	public static int nextInt()
	{
		return rnd.nextInt();
	}

	public static double nextDouble()
	{
		return rnd.nextDouble();
	}

	public static double nextGaussian()
	{
		return rnd.nextGaussian();
	}

	public static boolean nextBoolean()
	{
		return rnd.nextBoolean();
	}
}
