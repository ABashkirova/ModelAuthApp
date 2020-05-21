class Page extends React.Component {
    render() {
        return (
            <div>
                <Form/>
                <Table/>
            </div>
        )
    }
}

ReactDOM.render(
    <Page/>,
    document.getElementById('root')
);
