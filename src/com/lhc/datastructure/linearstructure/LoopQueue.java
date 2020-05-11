package com.lhc.datastructure.linearstructure;

/**
 * 循环队里
 * front == tail 队列为空
 * front == (tail + 1) % data.length 队列满
 *
 * @param <E>
 */
public class LoopQueue<E> implements Queue<E> {

    private static final int MIN_SIZE = 10;
    private E[] data;
    private int front;
    private int tail;
//    private int size;

    public LoopQueue() {
        this(MIN_SIZE);
    }

    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];
        front = 0;
        tail = 0;
//        size = 0;
    }


    @Override
    public void enqueue(E e) {
        if ((tail + 1) % data.length == front) {
            resize(getCapacity() * 2);
        }
        data[tail] = e;
        tail = (tail + 1) % data.length;
//        size++;
    }

    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity + 1];
        int size = getSize();
        for (int i = 0; i < size; i++) {
            newData[i] = data[(i + front) % data.length];
        }

        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
//        size--;
        if (getSize() < getCapacity() / 4 && getCapacity() / 2 >= MIN_SIZE) {
            resize(getCapacity() / 2);
        }
        return ret;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            return null;
        }
        return data[front];
    }

    @Override
    public int getSize() {
        if (tail >= front) {
            return tail - front;
        } else {
            return data.length - front + tail;
        }
//        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    public int getCapacity() {
        return data.length - 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Queue: size = %d , capacity = %d\n", getSize(), getCapacity()));
        sb.append("front [");
        for (int i = 0; i < getSize(); i++) {
            sb.append(data[(i + front) % data.length]);
            if ((i + front + 1) % data.length != tail) {
                sb.append(", ");
            }
        }
        sb.append("] tail");
        return sb.toString();
    }

    public static void main(String[] args) {
        LoopQueue<Integer> stack = new LoopQueue<>();
        for (int i = 0; i < 11; i++) {
            stack.enqueue(i);
            System.out.println(stack);
            if (i % 3 == 2) {
                stack.dequeue();
                System.out.println(stack);
            }
        }

        for (int i = 0; i < 5; i++) {
            stack.enqueue(i);
        }
        System.out.println(stack);

        for (int i = 0; i < 8; i++) {
            stack.dequeue();
        }

        System.out.println(stack);
    }
}
