package com;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceDemoBeCall {

	public List<String> Demo(int d, short s, long l, demo2 demo2) {
		List<String> sss = new ArrayList<String>();
		sss.add("asd");
		return sss;
	}

}
