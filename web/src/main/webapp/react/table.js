class Table extends React.Component {
    constructor(props) {
      super(props);
      this.state = {contentType: 'Users'};
    }

    render() {
        const ct = this.state.contentType;
        if(ct == 'Activities') {
           return <Activities />
        } else if (ct == 'Accesses') {
           return <Accesses />
        } else {
           return <Users />;
        }
    }
}