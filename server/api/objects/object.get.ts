import type {Object} from '@/types/object'

export default defineEventHandler((event): Object[] => {
    const query = getQuery(event)
    const search = (query.search as string)?.toLowerCase() || ''
    console.log('Search query:', search)
    const objects: Object[]= [
        {
            id: 1,
            name: 'Marteau',
            image: 'https://i.pravatar.cc/150?img=12',
            description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        },
        {
            id: 2,
            name: 'Perceuse',
            image: 'https://i.pravatar.cc/150?img=12',
            description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        },{
            id: 3,
            name: 'Tondeuse',
            image: 'https://i.pravatar.cc/150?img=12',
            description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        },{
            id: 4,
            name: 'Livre',
            image: 'https://i.pravatar.cc/150?img=12',
            description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        },
    ]
    if (!search) {
        return objects
    }

    return objects.filter(object =>
        object.name.toLowerCase().includes(search) ||
        object.description.toLowerCase().includes(search)
    )
})