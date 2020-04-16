package javautils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public class parser {
	Charset charset = Charset.forName("UTF-8");
	String rootPath = System.getProperty("user.dir");
	File makefile;
	HashMap<String, RoadNameAddrType> mHashMap1 = new HashMap<String, RoadNameAddrType>();
	HashMap<String, RoadNameAddrType> mHashMap2 = new HashMap<String, RoadNameAddrType>();
	HashMap<String, RoadNameAddrType> mHashMap3 = new HashMap<String, RoadNameAddrType>();
	HashMap<String, RoadNameAddrType> mHashMap4 = new HashMap<String, RoadNameAddrType>();

	public void LineParser(File file) {
		makefile = new File(rootPath, "make.csv");
		if (makefile.exists()) {
			makefile.delete();
		}
		try {
			System.out.println(file);
			BufferedReader BR = new BufferedReader(new InputStreamReader(new FileInputStream(file), "CP949"));
			String line;
			System.out.println("---------------------------------------------------------");
			while ((line = BR.readLine()) != null) {
				// System.out.println(line);
				// String[] addrs = line.split("\\|| ");
				String[] addrs = new String(line.getBytes("UTF-8")).split("\\|");

				if (!mHashMap1.containsKey(addrs[1].trim())) {
					RoadNameAddrType si = new RoadNameAddrType(AddrType.GwangyeogSi, 0, mHashMap1.size(),
							addrs[1].trim());
					mHashMap1.put(addrs[1].trim(), si);
					System.out.println(
							String.format("%s %s ,%s ,%s ,%s ,", mHashMap1.size(), addrs[1], "	", "	", "	"));
					write(si);

				}
				if (!mHashMap2.containsKey(addrs[2].trim())) {
					RoadNameAddrType parent = mHashMap1.get(addrs[1]);
					RoadNameAddrType si = new RoadNameAddrType(AddrType.GichoSi, parent.index, mHashMap2.size(),
							addrs[2].trim());
					mHashMap2.put(addrs[2].trim(), si);
					System.out.println(
							String.format("%s %s ,%s ,%s ,%s ,", mHashMap2.size(), "	", addrs[2], "	", "	"));

					write(si);
				}
				if (addrs[4].trim().length() != 0 && !mHashMap3.containsKey(addrs[4].trim())) {
					RoadNameAddrType parent = mHashMap2.get(addrs[2]);
					RoadNameAddrType si = new RoadNameAddrType(AddrType.EupMyeun, parent.index, mHashMap3.size(),
							addrs[4].trim());
					mHashMap3.put(addrs[4].trim(), si);
					System.out.println(String.format("%s = %s ,%s ,%s ,%s ,", mHashMap3.size(), "	", parent.index,
							addrs[4], "	"));
					write(si);
				} else if (addrs[4].trim().length() == 0 && !mHashMap3.containsKey(addrs[3].trim())) { // 읍면이 업사면 동을 넣는다
																										// .
					RoadNameAddrType parent = mHashMap2.get(addrs[2]);
					RoadNameAddrType si = new RoadNameAddrType(AddrType.EupMyeun, parent.index, mHashMap3.size(),
							addrs[3].trim());
					mHashMap3.put(addrs[3].trim(), si);
					System.out.println(String.format("%s , %s ,%s ,%s ,%s ,", mHashMap3.size(), "	", parent.index,
							addrs[3], "	"));
					write(si);

				}

				try {
					String road = addrs[9].trim();
					if (addrs[9].lastIndexOf("로") > 0) {
						road = addrs[9].trim().substring(0, addrs[9].lastIndexOf("로") + 1);
					} else if (addrs[9].lastIndexOf("길") > 0) {
						addrs[9] = addrs[9].replace("번길", "");
						road = addrs[9].trim().substring(0, addrs[9].lastIndexOf("길") + 1);
					} else {

					}

					if (!mHashMap4.containsKey(road)) {
						RoadNameAddrType parent = null;
						if (addrs[4].trim().length() > 0) {
							parent = mHashMap3.get(addrs[4]);
						} else {
							parent = mHashMap3.get(addrs[3]);
						}

						RoadNameAddrType si = new RoadNameAddrType(AddrType.RoadName, parent.index, mHashMap4.size(),
								road);
						mHashMap4.put(road.trim(), si);
						System.out.println(String.format("%s %s ,%s ,%s ,%s ,", mHashMap4.size(), "	", "	", "	",
								addrs[9]));
						write(si);

					}

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(String.format("%s, %s ,%s ,%s ,%s ,",  addrs[1],addrs[2], addrs[3],addrs[4], addrs[9]));
					break;
				}

				// break;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(RoadNameAddrType item) throws FileNotFoundException {

		Path ps = Paths.get(makefile.getPath());
		try {
			FileChannel channel = FileChannel.open(ps, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			channel.position(channel.size());
			item.AddrName = new String(item.AddrName);
			String content = String.format("%s, %s, %s, %s\n\r", item.mAddrType, item.index, item.parentIndex,
					item.AddrName);

			ByteBuffer byteBuffer = charset.encode(content);
			channel.write(byteBuffer);
			channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// https://ko.wikipedia.org/wiki/%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD%EC%9D%98_%EC%A3%BC%EC%86%8C
	/**
	 * 파일구조 1. 법정동 코드 2. 시도명 3. 시군구명 4. 법정읍면동명 5. 법리명 10. 도로명
	 * 
	 * 4511110100 |전라북도 |전주시 완산구|중앙동1가 |리 김해시 우암로
	 * 
	 * @author jipar
	 *
	 */
	public class RoadNameAddrType {
		AddrType mAddrType;
		int parentIndex = 0;// 상위 법정동
		String AddrName = "";
		int index;

		public RoadNameAddrType(AddrType type, int parentIndex, int myIndex, String name) {
			this.mAddrType = type;
			this.parentIndex = parentIndex;
			this.AddrName = name;
			this.index = myIndex;

		}
	}

	enum AddrType {
		// 광역자치단체 //기초자치단체 // 행정시 · 일반구 // 읍 · 면
		GwangyeogSi(0), GichoSi(1), SiGu(2), EupMyeun(3), RoadName(4);

		int index = 0;

		AddrType(int i) {
			this.index = i;
		}

		public int getIndex() {
			return index;
		}
	}

}
