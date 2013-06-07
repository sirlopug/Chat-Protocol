package Messages;

public class NameCollisionMessage extends Message {
	String targetUsername = null;

	public static final long minSize = 128;

	public NameCollisionMessage(int _op, long _length, long _reserved,
			String _options, int[] body) {
		super(_op, _length, _reserved, _options);
		processBody(body);
		if (op != 14) {
			correct = false;
		}
	}

	public NameCollisionMessage(int _op, long _length, long _reserved,
			String _options, String _targetUsername) {
		super(_op, _length, _reserved, _options);
		targetUsername = _targetUsername;
		if (op != 14) {
			correct = false;
		}
	}

	private void processBody(int[] body) {
		if (body.length != 128) {
			correct = false;
			return;
		}

		int[] targetUserArray = new int[128];
		for (int i = 0; i < body.length; i++) {
			targetUserArray[i] = body[i];
		}
		targetUsername = new String(targetUserArray, 0, targetUserArray.length);
		
	}

	public byte[] convert() {
		byte[] upper = super.convert();
		byte[] storage = new byte[(int) (upper.length + minSize)];
		for (int i = 0; i < upper.length; i++) {
			storage[i] = upper[i];
		}

		int total = upper.length - 1;

		byte[] tmp = null;

		tmp = targetUsername.getBytes();
		for (int i = 0; i < tmp.length; i++) {
			storage[total + i] = tmp[i];
		}

		total += 128;

		return storage;
	}
}
