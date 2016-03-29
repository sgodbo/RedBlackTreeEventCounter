import java.io.*;
import java.util.*;

public class bbst {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File inpFile = new File(args[0]);
		BufferedReader br1 = new BufferedReader(new FileReader(inpFile));

		String inp;
		RBTree r1 = new RBTree();
		String[] splitArr;
		int sizeOfInput = Integer.parseInt(br1.readLine().trim());
		int maxHeight = (int) Math.ceil(LogBase(sizeOfInput, 2));
		int[] arr = new int[2 * sizeOfInput];
		int i = 0;
		while (null != (inp = br1.readLine())) {
			splitArr = inp.split(" ");
			RBTreeNode temp = new RBTreeNode(Integer.parseInt(splitArr[0]),
					Integer.parseInt(splitArr[1]));

			arr[i] = temp.getId();
			arr[i + 1] = temp.getCount();
			i += 2;
		}
		br1.close();
		r1.insertAsList(arr, maxHeight);
		eventOps(r1);
	}

	private static double LogBase(int sizeOfInput, int i) {
		// TODO Auto-generated method stub
		return Math.log(sizeOfInput) / Math.log(i);
	}

	private static void eventOps(RBTree r1) {
		// TODO Auto-generated method stub
		System.out.println("Inputs registered");
		Scanner in = new Scanner(System.in);
		String command = "";
		String[] inpArr;
		while (in.hasNextLine()) {
			command = in.nextLine();
			inpArr = command.split(" ");
			if (inpArr.length > 3 || inpArr.length < 1) {
				System.out.println("invalid input");
			} else {
				if (command.contains("increase")) {
					int arg1 = Integer.parseInt(inpArr[1]);
					int arg2 = Integer.parseInt(inpArr[2]);
					r1.Increase(arg1, arg2);
				} else if (command.contains("reduce")) {
					int arg1 = Integer.parseInt(inpArr[1]);
					int arg2 = Integer.parseInt(inpArr[2]);
					r1.Reduce(arg1, arg2);
				} else if (command.contains("inrange")) {
					int arg1 = Integer.parseInt(inpArr[1]);
					int arg2 = Integer.parseInt(inpArr[2]);
					r1.InRange(arg1, arg2);
				} else if (command.contains("next")) {
					if (inpArr.length > 2) {
						System.out.println("invalid input");
					} else {
						int arg1 = Integer.parseInt(inpArr[1]);
						r1.Next(arg1);
					}
				} else if (command.contains("previous")) {
					if (inpArr.length > 2) {
						System.out.println("invalid input");
					} else {
						int arg1 = Integer.parseInt(inpArr[1]);
						r1.Previous(arg1);
					}
				} else if (command.contains("count")) {
					if (inpArr.length > 2) {
						System.out.println("invalid input");
					} else {
						int arg1 = Integer.parseInt(inpArr[1]);
						r1.Count(arg1);
					}
				} else if (command.contains("quit")) {
					if (inpArr.length > 2) {
						System.out.println("invalid input");
					} else {
						break;
					}
				} else {
					System.out.println("invalid input");
				}
			}
		}

		in.close();

	}
}
