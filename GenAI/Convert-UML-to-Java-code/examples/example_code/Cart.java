public class Cart {
	private List<Media> itemsOrdered = new ArrayList<Media>(); 
	
	public void addMedia(Media item) {
		itemsOrdered.add(item);
	}
	
	public void removeMedia(Media item) {
		itemsOrdered.remove(item);
	}
	
	public float totalCost() {
		float total = 0;
		for(Media media : itemsOrdered){
			total += media.getCost();
		}
		return total;
	}
	
	public void getAllTitles() {
		for(Media media : itemsOrdered){
			System.out.println(media.getTitle());
		}
	}
	
	public void print() {
		for(Media media : itemsOrdered){
			System.out.println(media);
		}
	}
	
	public void search(int id) {
		for(Media media : itemsOrdered){
			if(media.getId() == id){
				System.out.println(media);
				return;
			}
		}
		System.out.println("Media not found");
	}
	
	public void search(String title) {
		for(Media media : itemsOrdered){
			if(media.getTitle().equals(title)){
				System.out.println(media);
				return;
			}
		}
		System.out.println("Media not found");
	}
	
	public void sortByTitleCost() {
		Collections.sort(itemsOrdered, (a, b) -> a.getTitle().compareTo(b.getTitle()));
		Collections.sort(itemsOrdered, (a, b) -> Float.compare(a.getCost(), b.getCost()));

	}
	
	public void sortByCostTitle() {
		Collections.sort(itemsOrdered, (a, b) -> Float.compare(a.getCost(), b.getCost()));
		Collections.sort(itemsOrdered, (a, b) -> a.getTitle().compareTo(b.getTitle()));
	}
	
	public int getNumberOfMedia() {
		return itemsOrdered.size();
	}
	
	public void empty() {
		itemsOrdered.clear();
	}
}
