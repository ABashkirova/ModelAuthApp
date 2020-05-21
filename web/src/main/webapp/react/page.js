class Page extends React.Component {
    render() {
        return (
            <div>
                <p>Hello from Model Auth App</p>
                <Table/>
                <Form/>
            </div>
        )
    }
}

ReactDOM.render(
    <Page/>,
    document.getElementById('root')
);
