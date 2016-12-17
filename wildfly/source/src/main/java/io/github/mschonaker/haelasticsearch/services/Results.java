package io.github.mschonaker.haelasticsearch.services;

import java.util.List;

public class Results<T> {

	private final long total;

	private final List<T> items;

	public Results(long total, List<T> items) {
		this.total = total;
		this.items = items;
	}

	public List<T> getItems() {
		return items;
	}

	public long getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return "Results [total=" + total + ", items=" + items + "]";
	}
}
