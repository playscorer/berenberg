package berenberg;

public class Item {

    enum Type {
	Book, DVD, VHS, CD
    }

    private int id;

    private Type type;

    private String title;

    public Item(int id, Type type, String title) {
	this.id = id;
	this.type = type;
	this.title = title;
    }

    public int getId() {
	return id;
    }

    public Type getType() {
	return type;
    }

    public String getTitle() {
	return title;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((title == null) ? 0 : title.hashCode());
	result = prime * result + ((type == null) ? 0 : type.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Item other = (Item) obj;
	if (id != other.id)
	    return false;
	if (title == null) {
	    if (other.title != null)
		return false;
	} else if (!title.equals(other.title))
	    return false;
	if (type != other.type)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Item [id=" + id + ", type=" + type + ", title=" + title + "]";
    }

}
