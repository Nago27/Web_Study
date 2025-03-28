// chapter6 확인 문제 5번
const books = [{
    name: '혼자 공부하는 파이썬',
    price: 18000,
    publisher: '한빛미디어'
},{
    name: 'HTML5 웹 프로그래밍 입문',
    price: 26000,
    publisher: '한빛아카데미'
}, {
    name: '머신러닝 딥러닝 실전',
    price: 30000,
    publisher: '위키북스'
}]

console.log(_.orderBy(books, (book) => books.name))