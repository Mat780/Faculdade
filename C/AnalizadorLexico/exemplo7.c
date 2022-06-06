int main(){
	a = 10;
	switch(1+1) {
		case 0:
			a = 10;
		case (1+1):
			b = 20;
			break;
		case 3:
		case 4:
		case 5:
			switch(a) {
				case 0:
				case 1:
				case 2:
					break;
			}
			break;
	}

}