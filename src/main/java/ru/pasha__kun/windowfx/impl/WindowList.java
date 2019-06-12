package ru.pasha__kun.windowfx.impl;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import ru.pasha__kun.windowfx.Window;

import java.util.*;

public class WindowList implements ObservableList<Window> {
    private class WrapperChange extends ListChangeListener.Change<Window> {
        private final ListChangeListener.Change<? extends WindowEntry> change;
        private int[] permutation;
        private List<Window> removed;

        private WrapperChange(ListChangeListener.Change<? extends WindowEntry> change) {
            super(WindowList.this);
            this.change = change;
        }

        @Override public boolean next() {
            permutation = null;
            removed = null;
            return change.next();
        }

        @Override
        public void reset() {
            change.reset();
        }

        @Override
        public int getFrom() {
            return change.getFrom();
        }

        @Override
        public int getTo() {
            return change.getTo();
        }

        @Override
        public List<Window> getRemoved() {
            if (removed == null) {
                if (change.wasRemoved()) {
                    removed = new ArrayList<>(change.getRemovedSize());

                    for (WindowEntry e : change.getRemoved())
                        removed.add(e.getWindow());

                } else removed = Collections.emptyList();
            }

            return removed;
        }

        @Override
        protected int[] getPermutation() {
            if (permutation == null) {
                if (change.wasPermutated()) {
                    permutation = new int[change.getTo() - change.getFrom()];
                    for (int i = 0; i < permutation.length; i++)
                        permutation[i] = change.getPermutation(i + change.getFrom());

                } else permutation = new int[0];
            }
            return permutation;
        }
    }

    private class EventHelper implements ListChangeListener<WindowEntry> {
        private Set<ListChangeListener<? super Window>> handlers = new HashSet<>();

        @Override
        public void onChanged(Change<? extends WindowEntry> change) {
            ListChangeListener.Change<Window> change2 = new WrapperChange(change);

            for (ListChangeListener<? super Window> handler : handlers)
                handler.onChanged(change2);
        }
    }

    private class WindowEntryIterator implements ListIterator<Window> {
        private final ListIterator<WindowEntry> iterator;

        private WindowEntryIterator() {
            this.iterator = entries.listIterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Window next() {
            return iterator.next().getWindow();
        }

        @Override
        public boolean hasPrevious() {
            return iterator.hasPrevious();
        }

        @Override
        public Window previous() {
            return iterator.previous().getWindow();
        }

        @Override
        public int nextIndex() {
            return iterator.nextIndex();
        }

        @Override
        public int previousIndex() {
            return iterator.previousIndex();
        }

        @Override
        public void remove() {
            iterator.remove();
        }

        @Override
        public void set(Window window) {
            iterator.set(makeEntry(window));
        }

        @Override
        public void add(Window window) {
            iterator.add(makeEntry(window));
        }
    }

    private final ObservableList<WindowEntry> entries;
    private final EventHelper helper;

    public WindowList(ObservableList<WindowEntry> entries) {
        this.entries = entries;
        this.helper = new EventHelper();
        entries.addListener(helper);
    }

    private WindowEntry makeEntry(Window w){
        return new WindowEntry(w);
    }

    @Override
    public boolean setAll(Collection<? extends Window> collection) {
        throw new UnsupportedOperationException("setAll");
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        return entries.removeIf(e -> !c.contains(e.getWindow()));
    }

    @Override
    public ListIterator<Window> listIterator() {
        return new WindowEntryIterator();
    }

    @Override
    public boolean retainAll(Window... windows) {
        return retainAll(Arrays.asList(windows));
    }

    @Override
    public List<Window> subList(int fromIndex, int toIndex) {
        return new ArrayList<>(this).subList(fromIndex, toIndex);
    }

    @Override
    public boolean setAll(Window... windows) {
        return setAll(Arrays.asList(windows));
    }

    @Override
    public boolean add(Window window) {
        return entries.add(makeEntry(window));
    }

    @Override
    public Window set(int index, Window element) {
        WindowEntry e = entries.set(index, makeEntry(element));
        return e == null? null: e.getWindow();
    }

    @Override
    public void add(int index, Window element) {
        entries.add(index, makeEntry(element));
    }

    @Override
    public void addListener(ListChangeListener<? super Window> listChangeListener) {
        helper.handlers.add(listChangeListener);
    }

    @Override
    public void removeListener(ListChangeListener<? super Window> listChangeListener) {
        helper.handlers.remove(listChangeListener);
    }

    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i > -1) remove(i);
        return i > -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        ListIterator<Window> wi = listIterator(size() - 1);

        for (int i = size() - 1; wi.hasPrevious(); i--)
            if (Objects.equals(wi.previous(), o))
                return i;

        return -1;
    }

    @Override
    public int indexOf(Object o) {
        Iterator<Window> wi = iterator();

        for (int i = 0; wi.hasNext(); i++)
            if (Objects.equals(wi.next(), o))
                return i;

        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) > -1;
    }

    @Override
    public Object[] toArray() {
        return toArray(new Window[size()]);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Iterator<Window> wi = iterator();

        for (int i = 0; i < a.length && wi.hasNext(); i++)
            //noinspection unchecked
            a[i] = (T) wi.next();

        return a;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        entries.addListener(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        entries.removeListener(invalidationListener);
    }

    @Override
    public ListIterator<Window> listIterator(int index) {
        ListIterator<Window> i = listIterator();

        for (int j = 0; j < index; j++)
            i.next();

        return i;
    }

    @Override
    public Iterator<Window> iterator() {
        return listIterator();
    }

    @Override
    public Window get(int index) {
        return entries.get(index).getWindow();
    }

    @Override
    public Window remove(int index) {
        return entries.remove(index).getWindow();
    }

    @Override
    public void remove(int i, int i1) {
        entries.remove(i, i1);
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean removeAll(Window... windows) {
        boolean c = false;

        for (Window w : windows)
            if (remove(w))
                c = true;

        return c;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean e = false;

        for (Object w : c)
            if (remove(w))
                e = true;

        return e;
    }

    @Override
    public boolean addAll(Collection<? extends Window> c) {
        boolean e = false;

        for (Object w : c)
            if (w instanceof Window && add((Window) w))
                e = true;

        return e;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Window> c) {
        boolean e = false;

        for (Object w : c)
            if (w instanceof Window) {
                add(index++, (Window) w);
                e = true;
            }

        return e;
    }

    @Override
    public boolean addAll(Window... windows) {
        boolean c = false;

        for (Window w : windows)
            if (add(w))
                c = true;

        return c;
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public void clear() {
        entries.clear();
    }
}
