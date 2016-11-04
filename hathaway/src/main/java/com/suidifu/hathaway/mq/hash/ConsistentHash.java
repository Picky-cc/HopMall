package com.suidifu.hathaway.mq.hash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import com.suidifu.hathaway.mq.RemoteServiceAccessor;

public class ConsistentHash {
	private final HashFunction hashFunction;
	private final int numberOfReplicas;
	private final SortedMap<Integer, RemoteServiceAccessor> circle = new TreeMap<Integer, RemoteServiceAccessor>();

	public ConsistentHash() {
		this(new HashFunction(), 1000, null);
	}

	public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<RemoteServiceAccessor> nodes) {
		this.hashFunction = hashFunction;
		this.numberOfReplicas = numberOfReplicas;
		if (nodes != null && nodes.size() > 0)
			for (RemoteServiceAccessor node : nodes)
				add(node);
	}

	public void add(RemoteServiceAccessor node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.put(hashFunction.hash(node.toString() + i), node);
		}
	}

	public void remove(RemoteServiceAccessor node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.remove(hashFunction.hash(node.toString() + i));
		}
	}

	public RemoteServiceAccessor get(String referenceUuid) {
		if (circle.isEmpty()) {
			return null;
		}
		int hash = hashFunction.hash(referenceUuid);
		if (!circle.containsKey(hash)) {
			SortedMap<Integer, RemoteServiceAccessor> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}

	public int getNodeSize() {
		return this.circle.size();
	}
}
