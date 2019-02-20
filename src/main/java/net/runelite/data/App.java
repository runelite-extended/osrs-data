/*
 * MIT License
 *
 * Copyright (c) 2018 Tomas Slusny
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.runelite.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import net.runelite.cache.fs.Store;
import net.runelite.data.dump.MediaWiki;
import net.runelite.data.dump.wiki.ItemLimitsDumper;
import net.runelite.data.dump.wiki.ItemStatsDumper;

public class App
{
	public static final Gson GSON = new GsonBuilder()
		.setPrettyPrinting()
		.disableHtmlEscaping()
		.create();

	public static void main(String[] args) throws IOException
	{
		final File home = new File(System.getProperty("user.home"));
		final Store cacheStore = new Store(new File(home,
			"jagexcache" + File.separator + "oldschool" + File.separator + "LIVE"));
		cacheStore.load();

		// Try to make this go faster (probably not very smart)
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");

		final MediaWiki wiki = new MediaWiki("https://oldschool.runescape.wiki");
		ItemLimitsDumper.dump(cacheStore, wiki);
		ItemStatsDumper.dump(cacheStore, wiki);
	}
}
