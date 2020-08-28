package com.rookie.opcua.utils;

import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A globally unique identifier for objects.
 * <p>
 * Consists of 12 bytes, divided as follows: <blockquote>
 * 
 * <pre>
 * <table border="1">
 * <tr><td>0</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td>
 *     <td>7</td><td>8</td><td>9</td><td>10</td><td>11</td></tr>
 * <tr><td colspan="4">time</td><td colspan="3">machine</td>
 *     <td colspan="2">pid</td><td colspan="3">inc</td></tr>
 * </table>
 * </pre>
 * 
 * </blockquote>
 * 
 * @dochub objectids
 */
public class ObjectId implements java.io.Serializable {

	private static final long serialVersionUID = -4415279469780082174L;

	static final Logger LOGGER = Logger.getLogger(ObjectId.class.getName());

	/**
	 * Gets a new object id.
	 * 
	 * @return the new id
	 */
	public static ObjectId get() {
		return new ObjectId();
	}

	/**
	 * Create a new object id.
	 */
	public ObjectId() {
		_time = (int) (System.currentTimeMillis() / 1000);
		_machine = _genmachine;
		_inc = _nextInc.getAndIncrement();
		_new = true;
	}

	public static void main(String[] args) {
		System.out.println(new ObjectId().toHexString());
		System.out.println(new ObjectId().toHexString());
	}

	/**
	 * Converts this instance into a 24-byte hexadecimal string representation.
	 * 
	 * @return a string representation of the ObjectId in hexadecimal format
	 */
	public String toHexString() {
		final StringBuilder buf = new StringBuilder(24);

		byte b[] = new byte[12];
		ByteBuffer bb = ByteBuffer.wrap(b);
		// by default BB is big endian like we need
		bb.putInt(_time);
		bb.putInt(_machine);
		bb.putInt(_inc);
		
		for (final byte by : b) {
			buf.append(String.format("%02x", by & 0xff));
		}

		return buf.toString();
	}

	final int _time;
	final int _machine;
	final int _inc;

	boolean _new;

	private static AtomicInteger _nextInc = new AtomicInteger(
			RandUtil.getRandomNum(10));

	private static final int _genmachine;
	static {

		try {
			// build a 2-byte machine piece based on NICs info
			int machinePiece;
			{
				try {
					StringBuilder sb = new StringBuilder();
					Enumeration<NetworkInterface> e = NetworkInterface
							.getNetworkInterfaces();
					while (e.hasMoreElements()) {
						NetworkInterface ni = e.nextElement();
						sb.append(ni.toString());
					}
					machinePiece = sb.toString().hashCode() << 16;
				} catch (Throwable e) {
					// exception sometimes happens with IBM JVM, use random
					LOGGER.log(Level.WARNING, e.getMessage(), e);
					machinePiece = (RandUtil.getRandomNum(10)) << 16;
				}
				LOGGER.fine("machine piece post: "
						+ Integer.toHexString(machinePiece));
			}

			// add a 2 byte process piece. It must represent not only the JVM
			// but the class loader.
			// Since static var belong to class loader there could be collisions
			// otherwise
			final int processPiece;
			{
				int processId = RandUtil.getRandomNum(10);
				try {
					processId = java.lang.management.ManagementFactory
							.getRuntimeMXBean().getName().hashCode();
				} catch (Throwable t) {
				}

				ClassLoader loader = ObjectId.class.getClassLoader();
				int loaderId = loader != null ? System.identityHashCode(loader)
						: 0;

				StringBuilder sb = new StringBuilder();
				sb.append(Integer.toHexString(processId));
				sb.append(Integer.toHexString(loaderId));
				processPiece = sb.toString().hashCode() & 0xFFFF;
				LOGGER.fine("process piece: "
						+ Integer.toHexString(processPiece));
			}

			_genmachine = machinePiece | processPiece;
			LOGGER.fine("machine : " + Integer.toHexString(_genmachine));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
