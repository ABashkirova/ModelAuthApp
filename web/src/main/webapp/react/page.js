class Page extends React.Component {
    render() {
        return (
            <div>
                <nav className="navbar navbar-dark bg-secondary mb-3">
                    <span className="navbar-brand">Model Auth App</span>
                </nav>
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
